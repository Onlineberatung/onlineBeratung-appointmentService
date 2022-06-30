package com.vi.appointmentservice.scheduler;



import com.github.caldav4j.methods.HttpGetMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.fortuna.ical4j.model.Calendar;

import org.springframework.stereotype.Service;

import com.github.caldav4j.methods.CalDAV4JMethodFactory;
import com.github.caldav4j.*;
import net.fortuna.ical4j.data.CalendarBuilder;


import java.net.http.HttpResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class poc_caldav {

   public Calendar testGetCalendar() {
      String uri = "http://console.calcom-develop.suchtberatung.digital";

      CalDAV4JMethodFactory factory = new CalDAV4JMethodFactory();

      HttpGetMethod method = factory.createGetMethod(uri);

      Calendar calendar = null;

      // HttpResponse response = httpClient.execute(method);

      //Calendar calendar = method.getResponseBodyAsCalendar(response);

      return calendar;
   }


}
