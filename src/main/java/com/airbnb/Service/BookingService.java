package com.airbnb.Service;

import com.airbnb.Entity.Booking;
import com.airbnb.Entity.Property;
import com.airbnb.Entity.PropertyUser;
import com.airbnb.Payload.BookingDto;
import com.airbnb.Payload.BookingPdf;
import com.airbnb.repository.BookingRepository;
import com.airbnb.repository.PropertyRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.time.LocalDate;

@Service
public class BookingService {

    private BookingRepository bookingRepository;
    private PropertyRepository propertyRepository;
    private PdfService pdfService;

    private BucketService bucketService;

    public BookingService(BookingRepository bookingRepository, PropertyRepository propertyRepository, PdfService pdfService, BucketService bucketService) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.pdfService = pdfService;
        this.bucketService = bucketService;
    }

    public Booking createBooking(BookingDto bookingDto, Long propertyId, PropertyUser user) {
        Booking booking=new Booking();
        booking.setGuestName(bookingDto.getGuest());
        booking.setNumberOfNights(bookingDto.getNumberOfNights());
        booking.setCheckInDate(bookingDto.getCheckInDate());

        Property property=propertyRepository.findById(propertyId).get();
        Integer totalPrice= bookingDto.getNumberOfNights()*property.getNightPrice();
        System.out.println(totalPrice);
        booking.setTotalPrice(totalPrice);
        LocalDate totalNoDays=bookingDto.getCheckInDate().plusDays(bookingDto.getNumberOfNights());
        booking.setCheckOut(totalNoDays);
        booking.setProperty(property);
        booking.setPropertyUser(user);
        Booking book = bookingRepository.save(booking);
        BookingPdf pdf=new BookingPdf();
        pdf.setGuestName(book.getGuestName());
        pdf.setCheckInDate(book.getCheckInDate());
        pdf.setCheckOut(book.getCheckOut());
        pdf.setTotalPrice(book.getTotalPrice());
       boolean b = pdfService.generatePdf("D:\\PAS PROJECT\\"+"booking-conformation"+book.getGuestName()+".pdf",pdf);
        return book;
    }
}
