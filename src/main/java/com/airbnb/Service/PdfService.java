package com.airbnb.Service;

import com.airbnb.Payload.BookingPdf;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
@Service
public class PdfService {


    public boolean generatePdf(String fileName, BookingPdf pdf){

            try{
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(fileName));

                document.open();
                Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
                Chunk bookingConformation = new Chunk("Booking Conformation ", font);
                Chunk gustName = new Chunk("Guest Name -"+pdf.getGuestName(), font);
                Chunk checkInDate = new Chunk("CheckIn Date -"+pdf.getCheckInDate(), font);
                Chunk checkOut = new Chunk("CheckOut Date -"+pdf.getCheckOut(), font);
                Chunk totalPrice = new Chunk("Total Cost -"+pdf.getTotalPrice(), font);


                document.add(bookingConformation);
                document.add(new Paragraph("\n"));
                document.add(gustName);
                document.add(new Paragraph("\n"));
                document.add(checkInDate);
                document.add(new Paragraph("\n"));
                document.add(checkOut);
                document.add(new Paragraph("\n"));
                document.add(totalPrice);
                document.close();
                return true;
            }catch(Exception e){
e.printStackTrace();
            }
            return false;

}}
