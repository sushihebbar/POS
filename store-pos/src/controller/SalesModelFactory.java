package controller;

import model.SalesModel;

public class SalesModelFactory {

    public static SalesModel createSalesModel(long orderId, String invoiceDate, String partyName, String currency, float taux, float totalQuantity, float totalAmount, float otherAmount, float totalPaybleAmount, float totalPaidAmount, float totalDueAmount) {
        // Create and return a new SalesModel instance with the provided parameters
        return new SalesModel(orderId, invoiceDate, partyName, currency, taux, totalQuantity, totalAmount, otherAmount, totalPaybleAmount, totalPaidAmount, totalDueAmount);
    }
}
