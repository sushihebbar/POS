/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import helper.AlertFacade;
import database.DbConnection;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import java.util.HashSet;

import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;
import model.Item;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import model.ItemModel;

/**
 *
 * @author 650_654_674_625
 */
public class AddItemViewController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private TextField textFieldItem;

    @FXML
    private ComboBox comboBoxUom;

    @FXML
    private TextField textFieldQty;

  
    @FXML
    private TextField textFieldPrice;

    @FXML
    private TableView<ItemModel> tableViewItem;

  
    @FXML
    private TextField textFieldTotalQuantity;

    @FXML
    private TextField textFieldTotalAmount;

    @FXML
    private Button buttonSave;

    @FXML
    private TextField textFieldTotalOther;
    @FXML
    private TextField textFieldItemCode;
     @FXML
    private TextField textFieldItemCode1;
    @FXML
    private TextField textFieldTotalPaybleAmount;
@FXML
    private TextField textFieldItemName;

    
    @FXML
    private TextField textFieldTotalPaidAmount;

    @FXML
    private TextField textFieldTotalDueAmount;

    @FXML
    private TextField textFieldParty;

    @FXML
    private TextField textFieldContact;

    @FXML
    private TextField textFieldRemarks;
    
    @FXML
    private DatePicker date;

    Set<String> items = new HashSet<>();
    SuggestionProvider<String> provider = SuggestionProvider.create(items);
    private AutoCompletionBinding<String> autoCompleteBinding;

    Set<String> customers = new HashSet<>();
    SuggestionProvider<String> provider1 = SuggestionProvider.create(customers);
    private AutoCompletionBinding<String> autoCompleteBinding1;

    private final Connection con;

    private int selectedTableViewRow = 0;
    private long itemId;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        String rightPositionCSS = "-fx-alignment: CENTER-RIGHT;";
        String centerPostionCSS = "-fx-alignment: CENTER;";
//        AutoCompletionTextFieldBinding test = new AutoCompletionTextFieldBinding<>(textFieldItem, provider);
        
//        AutoCompletionTextFieldBinding test1 = new AutoCompletionTextFieldBinding<>(textFieldParty, provider1);
//        test1.setOnAutoCompleted(e -> setCustomer());

        TableColumn<ItemModel, String> columnItemCode = new TableColumn<>("ItemCode");
        columnItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
//        columnItemCode.setPrefWidth(400);

        TableColumn<ItemModel, String> columnItemName = new TableColumn<>("ItemName");
        columnItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<ItemModel, Integer> columnpackSize = new TableColumn<>("packsize");
        columnpackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        columnpackSize.setStyle(rightPositionCSS);
        
        TableColumn<ItemModel, Float> columnPrice = new TableColumn<>("mrp");
                columnPrice.setCellValueFactory(new PropertyValueFactory<>("mrp"));
                columnPrice.setStyle(rightPositionCSS);
                
       
        

        tableViewItem.getColumns().add(columnItemCode);

        tableViewItem.getColumns().add(columnItemName);
        tableViewItem.getColumns().add(columnpackSize);
       
        tableViewItem.getColumns().add(columnPrice);
       
System.out.println("in initialize2 \n");
       
    }

    

    public AddItemViewController() {
        DbConnection dbc = DbConnection.getDatabaseConnection();
        con = dbc.getConnection();
    }

    
   
    public void addItemInTableView() {
        if (selectedTableViewRow != 0) {
            int selectedRowNum = tableViewItem.getSelectionModel().getSelectedIndex();
            tableViewItem.getItems().remove(selectedRowNum);
            
            
            tableViewItem.getItems().add(selectedRowNum, 
                    new ItemModel(textFieldItemCode.getText(),
                    textFieldItemCode1.getText(),
                    Integer.parseInt(textFieldQty.getText()), 
                    Float.parseFloat(textFieldPrice.getText())));
            
        } 
        else {
tableViewItem.getItems().add( 
                    new ItemModel(textFieldItemCode.getText(),
                    (String) textFieldItemCode1.getText(),
                    Integer.parseInt(textFieldQty.getText()), 
                    Float.parseFloat(textFieldPrice.getText())));
        }
        this.clearHeaderForm();
        
    }

    public void clearHeaderForm() {
        textFieldItemCode.clear();
        
        textFieldItemCode1.clear();
           textFieldQty.clear();
        textFieldItem.requestFocus();
       textFieldPrice.clear();
        tableViewItem.scrollTo(tableViewItem.getItems().size());
        this.selectedTableViewRow = 0;
        itemId = 0;
    }

   

       

    public void deleteTableViewRow() {
        int selectedRowNum = tableViewItem.getSelectionModel().getSelectedIndex();
        if (selectedRowNum >= 0) {
            tableViewItem.getItems().remove(selectedRowNum);
        }
        this.clearHeaderForm();
    }

    @FXML
    private void save() {
        Window owner = buttonSave.getScene().getWindow();
        LocalDate documentDate = LocalDate.now();
        String query="";
        ResultSet rs1;
        Statement stmt; 
        String itempricesquery="";
int count = 0;
            int pcount = 0;
        try {
            stmt = con.createStatement();
            System.out.println("created");
            rs1 = stmt.executeQuery("select purchases_order_id.nextval from dual");
            rs1.next();
            int posSequence = rs1.getInt("nextval");
            
            query = "INSERT INTO pos.items (item_id, code, name, pack_unit_id, pack_size, standard_unit_id, created, modified) ";
            itempricesquery = "insert into pos.item_prices (item_price_id,item_id,sale_price,EFFECTIVE_FROM,EFFECTIVE_TO,status) ";
            
            for (ItemModel item : tableViewItem.getItems()) 
            {
               query = query+ "select " + posSequence + ", '"
             + item.getItemCode() + "', '"
             + item.getItemName() + "', 1, "
             + item.getPackSize() + ", 1, "
             + "to_date('"+documentDate+"','yyyy-mm-dd')" + ", "
             + "to_date('"+documentDate+"','yyyy-mm-dd')" + " from dual ";
               
               itempricesquery=itempricesquery + "select " + posSequence + ", "
             + posSequence + ", "
             + item.getMrp() + ", "
             + "to_date('"+documentDate+"','yyyy-mm-dd')" + ", "
             + "to_date('"+documentDate+"','yyyy-mm-dd')+1000 "
             + ",1" + " from dual ";
              
                
             if (count != (tableViewItem.getItems().size() - 1)) {
                    query += " union all ";
                }
                count++;
                if (pcount != (tableViewItem.getItems().size() - 1)) {
                    itempricesquery += " union all ";
                }
                pcount++;
            }
            
            
            System.out.println(query);
            rs1 = stmt.executeQuery(query);

           System.out.println(itempricesquery);
            rs1 = stmt.executeQuery(itempricesquery);

            if(count ==0)
        {
            AlertFacade.showAlert(Alert.AlertType.INFORMATION, owner, "Information",
                    "no item added");
            return;
        }


            AlertFacade.showAlert(Alert.AlertType.INFORMATION, owner, "Information",
                    "A record has been saved successfully.");
            
            clearFooterForm();
            textFieldItem.requestFocus();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void clearWholeForm() {
        clearHeaderForm();
        clearFooterForm();
        textFieldItem.requestFocus();
    }

    private void clearFooterForm() {
        tableViewItem.getItems().clear();
        textFieldTotalAmount.clear();
        textFieldTotalQuantity.clear();
        textFieldParty.clear();
        textFieldContact.clear();
        textFieldRemarks.clear();
        textFieldTotalAmount.clear();
        textFieldTotalDueAmount.clear();
        textFieldTotalOther.clear();
        textFieldTotalPaidAmount.clear();
        textFieldTotalPaidAmount.clear();
        textFieldTotalPaybleAmount.clear();
        textFieldTotalQuantity.clear();
    }

    private void setCustomer() {
    }

  

   
    
}
