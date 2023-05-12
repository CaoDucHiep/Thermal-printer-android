package com.caoduchiep.escposprinter.textparser;

import com.caoduchiep.escposprinter.EscPosPrinterCommands;
import com.caoduchiep.escposprinter.exceptions.EscPosConnectionException;
import com.caoduchiep.escposprinter.exceptions.EscPosEncodingException;

public interface IPrinterTextParserElement {
    int length() throws EscPosEncodingException;
    IPrinterTextParserElement print(EscPosPrinterCommands printerSocket) throws EscPosEncodingException, EscPosConnectionException;
}
