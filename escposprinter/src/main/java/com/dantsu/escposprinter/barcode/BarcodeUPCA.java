package com.caoduchiep.escposprinter.barcode;

import com.caoduchiep.escposprinter.EscPosPrinterCommands;
import com.caoduchiep.escposprinter.EscPosPrinterSize;
import com.caoduchiep.escposprinter.exceptions.EscPosBarcodeException;

public class BarcodeUPCA extends BarcodeNumber {

    public BarcodeUPCA(EscPosPrinterSize printerSize, String code, float widthMM, float heightMM, int textPosition) throws EscPosBarcodeException {
        super(printerSize, EscPosPrinterCommands.BARCODE_TYPE_UPCA, code, widthMM, heightMM, textPosition);
    }

    @Override
    public int getCodeLength() {
        return 12;
    }
}
