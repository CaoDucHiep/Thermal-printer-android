package com.caoduchiep.escposprinter.barcode;

import com.caoduchiep.escposprinter.EscPosPrinterSize;
import com.caoduchiep.escposprinter.EscPosPrinterCommands;
import com.caoduchiep.escposprinter.exceptions.EscPosBarcodeException;

public class Barcode128 extends Barcode {
    public Barcode128(EscPosPrinterSize printerSize, String code, float widthMM, float heightMM, int textPosition) throws EscPosBarcodeException {
        super(printerSize, EscPosPrinterCommands.BARCODE_TYPE_128, code, widthMM, heightMM, textPosition);
    }

    @Override
    public int getCodeLength() {
        return this.code.length();
    }

    @Override
    public int getColsCount() {
        return (this.getCodeLength() + 5) * 11;
    }
}