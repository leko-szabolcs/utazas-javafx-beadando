package com.example.utazasbeadandojavafx;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.account.AccountSummary;
import com.oanda.v20.order.MarketOrderRequest;

import com.oanda.v20.order.OrderCreateRequest;
import com.oanda.v20.order.OrderCreateResponse;
import com.oanda.v20.instrument.Candlestick;
import com.oanda.v20.primitives.InstrumentName;
import com.oanda.v20.instrument.CandlestickGranularity;
import com.oanda.v20.instrument.InstrumentCandlesRequest;
import com.oanda.v20.instrument.InstrumentCandlesResponse;
import com.oanda.v20.pricing.ClientPrice;
import com.oanda.v20.pricing.PricingGetRequest;
import com.oanda.v20.pricing.PricingGetResponse;
import com.oanda.v20.trade.Trade;
import com.oanda.v20.trade.TradeCloseRequest;
import com.oanda.v20.trade.TradeSpecifier;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class ForexController implements Initializable {

    Context ctx = new ContextBuilder(ForexConfig.URL).setToken(ForexConfig.TOKEN).setApplication("PricePolling").build();
    AccountID accountId = ForexConfig.ACCOUNTID;

    @FXML
    private TableView<ForexData> AcountDataTableView;
    @FXML
    private TableColumn<ForexData, String> AccountNameColumn;
    @FXML
    private TableColumn<ForexData, String> AccountValueColumn;

    //Aktualis arak
    @FXML
    private ComboBox<String> InstrumentsComboBox;
    @FXML
    private TableView<ForexData> InstrumentInformationsTableView;
    @FXML
    private TableColumn<ForexData, String> InstrumentDataNameColumn;
    @FXML
    private TableColumn<ForexData, String> instrumentDataValueColumn;
    @FXML
    private Label InstrumentResponseLabel;

    //Historikus árak
    @FXML
    private ComboBox<String> HistoricInstrumentsComboBox;
    @FXML
    private ComboBox<CandlestickGranularity> HistoricGranularityComboBox;
    @FXML
    private DatePicker StartDatePicker;
    @FXML
    private DatePicker CloseDatePicker;
    @FXML
    private Label HistoricResponseLabel;

    @FXML
    private LineChart ForexLineChart;
    @FXML
    private NumberAxis ForexYAxis;

    //Pocicio Nyitas
    @FXML
    private ComboBox<String> OpenPositionInstrumentsComboBox;
    @FXML
    private ComboBox<Integer> OpenPositionAmountComboBox;
    @FXML
    private ComboBox<String> OpenPositonDirrectionComboBox;
    @FXML
    private Label OpenPositionInstrumentsErrorLabel;
    @FXML
    private Label OpenPositionAmountErrorLabel;
    @FXML
    private Label OpenPositionDirrectionErrorLabel;
    @FXML
    private Label OpenPositionResponseLabel;

    //Pozicio zaras
    @FXML
    private TextField ClosePostionIdTextField;
    @FXML
    private  Label ClosePostionIdErrorLabel;
    @FXML
    private Label ClosePostionResponseLabel;

    //Nyitott Poziciok
    @FXML
    private TableView<Trade> TradesTableView;
    @FXML
    private TableColumn<Trade, String> TradeIdTableColumn;
    @FXML
    private TableColumn<Trade, String> TradeInstrumentTableColumn;
    @FXML
    private TableColumn<Trade, String> TradeDateTableColumn;
    @FXML
    private TableColumn<Trade, String> TradeCurrentUnitsTableColumn;
    @FXML
    private TableColumn<Trade, String> TradePriceTableColumn;
    @FXML
    private TableColumn<Trade, String> TradeUnrealizedPLTableColumn;

    public void loadAccountInformations() {
        try{

            AccountSummary summary = ctx.account.summary(new AccountID(ForexConfig.ACCOUNTID)).getAccount();
            String summaryString = summary.toString().trim();

            summaryString = summaryString.replaceFirst(".*?\\(", "").replaceFirst("\\)", "");
            String[] split = summaryString.split(",");

                List<ForexData> list = new ArrayList<>();
                for(String s : split){
                    String nameValue[] = s.split("=");
                    ForexData accountData = new ForexData(nameValue[0], nameValue[1]);
                    list.add(accountData);
                    ObservableList<ForexData> summaryList = FXCollections.observableArrayList(list);
                    AcountDataTableView.setItems(summaryList);
                }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadInstrumentInformations() {
        InstrumentResponseLabel.setText("");
        if (InstrumentsComboBox.getValue() == null){
            InstrumentResponseLabel.setText("Válassz ki egy deviza párt!");
            return;
        }
        try {
            List<String> instruments = new ArrayList<>( Arrays.asList(InstrumentsComboBox.getValue()));
            PricingGetRequest request = new PricingGetRequest(accountId, instruments);
            PricingGetResponse resp = ctx.pricing.get(request);

            ClientPrice price = resp.getPrices().get(0);
            String priceString = price.toString().trim();

            priceString = priceString.replaceFirst("ClientPrice\\(", "").replaceFirst("\\)$", "");
            String[] split = priceString.split(",");

            List<ForexData> list = new ArrayList<>();
            for(String s : split){
                ForexData accountData;
                String nameValue[] = s.split("=");
                if (nameValue.length > 2){
                    String ValueString = "";
                    for(int i=1 ; i<nameValue.length ; i++){
                        ValueString += nameValue[i];
                    }
                    accountData = new ForexData(nameValue[0], ValueString);
                }else {
                    accountData = new ForexData(nameValue[0], nameValue[1]);
                }
                list.add(accountData);
                ObservableList<ForexData> priceList = FXCollections.observableArrayList(list);
                InstrumentInformationsTableView.setItems(priceList);
                InstrumentResponseLabel.setText("Sikeres lekérdezés!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            InstrumentResponseLabel.setText("Elérési Hiba!");
        }
    }

    public void loadHistoric(){
        HistoricResponseLabel.setText("");
        if(HistoricInstrumentsComboBox.getValue() == null){
            HistoricResponseLabel.setText("Válassz ki deviza párt!");
            return;
        }
        if(StartDatePicker.getValue() == null || CloseDatePicker.getValue() == null){
            HistoricResponseLabel.setText("Válassz ki Kezdő és Záró dátumot!");
            return;
        }
        if(CloseDatePicker.getValue().isBefore(StartDatePicker.getValue())){
            HistoricResponseLabel.setText("Záró dátum kisebb mint a Kezdő dátum!");
            return;
        }
        if(StartDatePicker.getValue().isAfter(LocalDateTime.now().toLocalDate()) || CloseDatePicker.getValue().isAfter(LocalDateTime.now().toLocalDate())){
            HistoricResponseLabel.setText("Kezdes vagy a Zárás Dátuma még nem elérhető!");
            return;
        }
        if(HistoricGranularityComboBox.getValue() == null){
            HistoricResponseLabel.setText("Válassz ki gyakoriságot!");
            return;
        }
        try {
            InstrumentCandlesRequest request = new InstrumentCandlesRequest(new InstrumentName(HistoricInstrumentsComboBox.getValue()));
            request.setGranularity(HistoricGranularityComboBox.getValue());
            request.setFrom(StartDatePicker.getValue()+"T00:00:00");
            request.setTo(CloseDatePicker.getValue()+"T00:00:00");
            InstrumentCandlesResponse resp = ctx.instrument.candles(request);

            XYChart.Series series = new XYChart.Series();
            series.setName(HistoricInstrumentsComboBox.getValue());
            double minvalue = resp.getCandles().get(0).getMid().getC().doubleValue();
            double maxvalue = 0;
            for(Candlestick candle: resp.getCandles()){
                if( candle.getMid().getC().doubleValue() < minvalue ){
                    minvalue= candle.getMid().getC().doubleValue();
                }
                if( candle.getMid().getC().doubleValue() > maxvalue ){
                    maxvalue = candle.getMid().getC().doubleValue();
                }
            }
            ForexYAxis.setLowerBound(minvalue);
            ForexYAxis.setUpperBound(maxvalue);

            for(int i=0; i<resp.getCandles().size(); i++){
                double number = resp.getCandles().get(i).getMid().getC().doubleValue();
                String date = resp.getCandles().get(i).getTime().toString().replace("T"," ").substring(0, 16);
                series.getData().add(new XYChart.Data(date, number));
            }
            ForexLineChart.getData().clear();
            ForexLineChart.getData().add(series);

            HistoricResponseLabel.setText("");
        } catch (Exception e) {
            HistoricResponseLabel.setText("Lekérdezés hiba!\nVálasz kevesebb gyakoriságot ehhez az időintervallumhoz!");
        }
    }

    public void openPositon()   {
        OpenPositionInstrumentsErrorLabel.setText("");
        OpenPositionAmountErrorLabel.setText("");
        OpenPositionDirrectionErrorLabel.setText("");
        OpenPositionResponseLabel.setText("");

        boolean isValid = true;
        if(OpenPositionInstrumentsComboBox.getValue() == null){
            isValid =false;
            OpenPositionInstrumentsErrorLabel.setText("Válassz deviza párt!");
        }
        if(OpenPositionAmountComboBox.getValue() == null){
            isValid =false;
            OpenPositionAmountErrorLabel.setText("Add meg a kívánt mennyiséget!");
        }
        if(OpenPositonDirrectionComboBox.getValue() == null){
            isValid =false;
            OpenPositionDirrectionErrorLabel.setText("Add meg a pozíció irányát!");
        }
        if(!isValid){
            return;
        }

        try {
            InstrumentName instrument = new InstrumentName(OpenPositionInstrumentsComboBox.getValue());
            Integer unit = OpenPositionAmountComboBox.getValue();
            if (OpenPositonDirrectionComboBox.getValue().toString().equals("Eladás")){
                unit = unit*-1;
            }

            OrderCreateRequest request = new OrderCreateRequest(accountId);
            MarketOrderRequest marketorderrequest = new MarketOrderRequest();
            marketorderrequest.setInstrument(instrument);
            marketorderrequest.setUnits(unit);
            request.setOrder(marketorderrequest);
            OrderCreateResponse response = ctx.order.create(request);

            if(response == null){
                OpenPositionResponseLabel.setText("Nem sikerült létrehozni a pozíciót!");
                return;
            }
            if(response.getOrderFillTransaction()==null){
                OpenPositionResponseLabel.setText("Jelenleg a piac zárva van, próbáld meg később!");
                return;
            }
            OpenPositionResponseLabel.setText("Sikeres nyitottál egy pozíciót" +"tradeId: " + response.getOrderFillTransaction().getId()+"\n Nézd meg a Nyitott Poczícók menü alatt!");
        } catch (Exception e) {
            OpenPositionResponseLabel.setText("Nem sikerült létrehozni a pozíciót!");
            throw new RuntimeException(e);
        }
    }

    public void closePosition(){
        ClosePostionIdErrorLabel.setText("");
        int id ;
        if(ClosePostionIdTextField.getText().isEmpty() || ClosePostionIdTextField.getText() == null ){
            ClosePostionIdErrorLabel.setText("Add meg a pozíció ID-t!");
            return;
        }
        try{
            id = Integer.parseInt(ClosePostionIdTextField.getText());
        }
        catch(NumberFormatException e){
            ClosePostionIdErrorLabel.setText("Helyetelen pozíció ID!");
            return;
        }

        try {
            String tradeId = ClosePostionIdTextField.getText();
            ctx.trade.close(new TradeCloseRequest(accountId, new TradeSpecifier(tradeId)));
            ClosePostionResponseLabel.setText("Zárva sikeresen végrehajtva, " +"tradeId: "+tradeId);
        } catch (Exception e) {
            ClosePostionResponseLabel.setText("A zárást nem sikerül végrehajtani!");
        }
    }

    public void listPostions(){
        try{
            List<Trade> trades = ctx.trade.listOpen(accountId).getTrades();
            TradesTableView.setItems(FXCollections.observableArrayList(trades));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AccountNameColumn.setCellValueFactory(new PropertyValueFactory<ForexData,String>("name"));
        AccountValueColumn.setCellValueFactory(new PropertyValueFactory<ForexData,String>("value"));

        InstrumentDataNameColumn.setCellValueFactory(new PropertyValueFactory<ForexData,String>("name"));
        instrumentDataValueColumn.setCellValueFactory(new PropertyValueFactory<ForexData,String>("value"));

        TradeIdTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        TradeInstrumentTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInstrument().toString()));
        TradeDateTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOpenTime().toString()));
        TradeCurrentUnitsTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCurrentUnits().toString()));
        TradePriceTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrice().toString()));
        TradeUnrealizedPLTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrice().toString()));

        List<String> instruments = new ArrayList<>( Arrays.asList("EUR_USD", "USD_JPY","GBP_USD", "USD_CHF","NZD_USD","AUD_USD"));
        ObservableList instrumentsList = FXCollections.observableArrayList(instruments);
        InstrumentsComboBox.setItems(instrumentsList);
        HistoricInstrumentsComboBox.setItems(instrumentsList);
        OpenPositionInstrumentsComboBox.setItems(instrumentsList);

        List<Integer> amounts = new ArrayList<>(Arrays.asList(10,20,30,40,50,100,500,1000));
        ObservableList amountsList = FXCollections.observableArrayList(amounts);
        OpenPositionAmountComboBox.setItems(amountsList);

        List<String> dirrections = new ArrayList<>( Arrays.asList("Eladás", "Vásárlás"));
        ObservableList dirrectionsList = FXCollections.observableArrayList(dirrections);
        OpenPositonDirrectionComboBox.setItems(dirrectionsList);

        List<CandlestickGranularity> list = new ArrayList<>( Arrays.asList(CandlestickGranularity.values()));
        list = list.subList(8,list.size());
        HistoricGranularityComboBox.setItems( FXCollections.observableArrayList(list));
    }
}
