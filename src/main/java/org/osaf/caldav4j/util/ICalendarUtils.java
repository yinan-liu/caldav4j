/*
 * Copyright 2005 Open Source Applications Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.osaf.caldav4j.util;

import java.util.Calendar;

import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.component.VEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ICalendarUtils {
    private static final Log log = LogFactory.getLog(ICalendarUtils.class);
    
    private static java.util.TimeZone J_TZ_GMT = TimeZone.getTimeZone("GMT");
   
    /**
     * Creates an iCal4J DateTime. The values for year, month, day, hour,
     * minutes, seconds and milliseconds should be set way that you specify them
     * in a java.util.Calendar - which means zero indexed months for example
     * (eg. January is '0').
     * 
     * Note that the TimeZone is not a java.util.TimeZone but an iCal4JTimeZone
     * 
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minutes
     * @param seconds
     * @param milliseconds
     * @param tz
     * @param utc
     * @return
     */
    public static DateTime createDateTime(int year, int month, int day,
            int hour, int minutes, int seconds, int milliseconds, TimeZone tz,
            boolean utc) {
        DateTime dateTime = new DateTime();
        setFields(dateTime, year, month, day, hour, minutes, seconds,
                milliseconds, tz, utc);
        return dateTime;
    }
    
    public static DateTime createDateTime(int year, int month, int day,
            int hour, int minutes, TimeZone tz, boolean utc) {
        DateTime dateTime = new DateTime();
        setFields(dateTime, year, month, day, hour, minutes, 0, 0, tz, utc);
        return dateTime;

    }
    
    public static Date createDate(int year, int month, int day) {
        Date date = new Date();
        setFields(date, year, month, day, 0, 0, 0, 0, null, false);
        return date;

    }
    
    public static Date createDateTime(int year, int month, int day,
            TimeZone tz, boolean utc) {
        DateTime date = new DateTime();
        setFields(date, year, month, day, 0, 0, 0, 0, tz, utc);
        return date;

    }
    
    public static VEvent getFirstEvent(net.fortuna.ical4j.model.Calendar calendar){
        return (VEvent) calendar.getComponents().getComponent(Component.VEVENT);
    }
    
    public static String getSummaryValue(VEvent event){
        return getPropertyValue(event, Property.SUMMARY);
    }

    public static String getUIDValue(Component component){
        return getPropertyValue(component, Property.UID);
    }
    
    public static String getPropertyValue(Component component, String propertyName){
        Property property = component.getProperties().getProperty(propertyName);
        return property == null ? null : property.getValue();
    }
    
    private static void setFields(Date date, int year, int month, int day,
            int hour, int minutes, int seconds, int milliseconds, TimeZone tz,
            boolean utc){
        
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.setTimeZone(tz == null ? J_TZ_GMT : tz);
        if (date instanceof DateTime){
            if (utc) {
               ((DateTime)date).setUtc(utc);
            } else if (tz != null){
                ((DateTime)date).setTimeZone(tz);
            }
            calendar.set(Calendar.HOUR, hour);
            calendar.set(Calendar.MINUTE, minutes);
            calendar.set(Calendar.SECOND, seconds);
            calendar.set(Calendar.MILLISECOND, milliseconds);
        }
        date.setTime(calendar.getTimeInMillis());
    }
    
}
