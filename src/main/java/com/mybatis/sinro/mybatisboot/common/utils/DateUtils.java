package com.mybatis.sinro.mybatisboot.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * @ClassName: DateUtils
 * @Package com.tce.yunshi.common.util
 * @Description: 日期时间工具类
 * @Author wuxinjian
 * @Date 2018/11/1 11:05
 * @Version V1.0
 */
public class DateUtils {

    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String DATE_FORMAT_NO_MINUS = "yyyyMMdd";

    public static final String DATE_FORMAT_TIMESTAMP = "yyyyMMddHHmmss";

    /**
     * 当前时间LocalDateTime对象
     * @Title localDateTime
     * @return LocalDateTime
     * @author wuxinjian
     * @date 2018/11/1 11:16
     */
    public static LocalDateTime localDateTime(){
        return LocalDateTime.now();
    }

    /**
     * 当前时间Date对象
     * @Title date
     * @return Date
     * @author wuxinjian
     * @date 2018/11/1 11:16
     */
    public static Date date(){
        return new Date();
    }

    /**
     * 当前时间戳
     * @Title now
     * @return long
     * @author wuxinjian
     * @date 2018/11/1 11:16
     */
    public static long now(){
        return System.currentTimeMillis();
    }

    /**
     * 30天前的时间
     * @Title now
     * @return long
     * @author wuxinjian
     * @date 2018/11/1 11:16
     */
    public static LocalDateTime lastMonth(){
        LocalDateTime now = LocalDateTime.now();
        return now.minus(30, ChronoUnit.DAYS);
    }
    /**
     * day天前的时间
     * @Title now
     * @return long
     * @author wuxinjian
     * @date 2018/11/1 11:16
     */
    public static LocalDateTime lastDay(int day){
        LocalDateTime now = LocalDateTime.now();
        return now.minus(day, ChronoUnit.DAYS);
    }
    /**
     * year年前的时间
     * @Title now
     * @return long
     * @author wuxinjian
     * @date 2018/11/1 11:16
     */
    public static LocalDateTime lastYear(int year){
        LocalDateTime now = LocalDateTime.now();
        return now.minus(year, ChronoUnit.YEARS);
    }

    /**
     * Date转String字符串,默认格式yyyy-MM-dd HH:mm:ss
     * @Title getDateAsString
     * @param date
     * @return String
     * @author wuxinjian
     * @date 2018/11/1 11:16
     */
    public static String getDateAsString(Date date){
        return getDateAsString(DEFAULT_DATE_TIME_FORMAT, date);
    }

    /**
     * Date转String字符串
     * @Title getDateAsString
     * @param format
     * @param date
     * @return String
     * @author wuxinjian
     * @date 2018/11/1 11:16
     */
    public static String getDateAsString(String format, Date date){
        DateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
    /**
     * String字符串转Date,默认格式yyyy-MM-dd HH:mm:ss
     * @Title parseStringToDate
     * @param time
     * @return String
     * @author wuxinjian
     * @date 2018/11/1 11:16
     */
    public static Date parseStringToDate(String time) throws ParseException {
        return parseStringToDate(DEFAULT_DATE_TIME_FORMAT, time);
    }

    /**
     * String字符串转Date
     * @Title parseStringToDate
     * @param format
     * @param time
     * @return String
     * @author wuxinjian
     * @date 2018/11/1 11:16
     */
    public static Date parseStringToDate(String format, String time) throws ParseException {
        DateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(time);
    }

    /**
     * long型时间戳转Date
     * @Title getDateAsString
     * @param timestamp
     * @return LocalDateTime
     * @author wuxinjian
     * @date 2018/11/1 11:16
     */
    public static Date getDateOfTimestamp(long timestamp) {
        return new Date(timestamp);
    }

    /**
     * Date转long型时间戳
     * @Title getDateAsString
     * @param date
     * @return long
     * @author wuxinjian
     * @date 2018/11/1 11:16
     */
    public static long getTimestampOfDate(Date date) {
        return date.getTime();
    }


    /**
     * LocalDateTime转String字符串,默认格式yyyy-MM-dd HH:mm:ss
     * @Title getDateTimeAsString
     * @param localDateTime
     * @return String
     * @author wuxinjian
     * @date 2018/11/1 11:16
     */
    public static String dateTimeAsString(LocalDateTime localDateTime){
        return getDateTimeAsString(DEFAULT_DATE_TIME_FORMAT, localDateTime);
    }

    /**
     * LocalDateTime转String字符串,默认格式yyyy-MM-dd HH:mm:ss
     * @Title getDateTimeAsString
     * @param localDateTime
     * @return String
     * @author wuxinjian
     * @date 2018/11/1 11:16
     */
    public static String getDateTimeAsString(LocalDateTime localDateTime){
        return getDateTimeAsString(DEFAULT_DATE_TIME_FORMAT, localDateTime);
    }

    /**
     * LocalDateTime转String字符串
     * @Title getDateTimeAsString
     * @param format
     * @param localDateTime
     * @return String
     * @author wuxinjian
     * @date 2018/11/1 11:16
     */
    public static String getDateTimeAsString(String format, LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    /**
     * String字符串转LocalDateTime,默认格式yyyy-MM-dd HH:mm:ss
     * @Title getDateTimeAsString
     * @param time
     * @return LocalDateTime
     * @author wuxinjian
     * @date 2018/11/1 11:16
     */
    public static LocalDateTime parseStringToDateTime(String time) {
        return parseStringToDateTime(DEFAULT_DATE_TIME_FORMAT, time);
    }

    /**
     * String字符串转LocalDateTime
     * @Title getDateTimeAsString
     * @param time
     * @return LocalDateTime
     * @author wuxinjian
     * @date 2018/11/1 11:16
     */
    public static LocalDateTime parseStringToDateTime(String format, String time) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, df);
    }

    /**
     * long型时间戳转LocalDateTime
     * @Title getDateTimeAsString
     * @param timestamp
     * @return LocalDateTime
     * @author wuxinjian
     * @date 2018/11/1 11:16
     */
    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * LocalDateTime转long型时间戳
     * @Title getDateTimeAsString
     * @param localDateTime
     * @return long
     * @author wuxinjian
     * @date 2018/11/1 11:16
     */
    public static long toEpochMilli(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * LocalDateTime转long型时间戳
     * @Title getDateTimeAsString
     * @param localDateTime
     * @return long
     * @author wuxinjian
     * @date 2018/11/1 11:16
     */
    public static long toEpochSecond(LocalDateTime localDateTime) {
        return localDateTime.toEpochSecond(ZoneOffset.of("+8"));
    }

    public static Date nowBeforeYear(int years){
        return nowAfterYear(0 - years);
    }

    public static Date nowAfterYear(int years){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }

    public static LocalDateTime dayStart(LocalDateTime day){
        //某天零点
        return day.with(LocalTime.MIN);
    }


    public static LocalDateTime dayEnd(LocalDateTime day){
        //某天零点
        return day.with(LocalTime.MAX);
    }

    public static LocalDateTime todayStart(){
        //当天零点
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    }

    public static LocalDateTime todayEnd(){
        //当天24点
        return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    }

    public static int differDay(String startTime, String endTime) {
        LocalDate start = LocalDate.parse(startTime, DateTimeFormatter.ofPattern(DATE_FORMAT));
        LocalDate end = LocalDate.parse(endTime, DateTimeFormatter.ofPattern(DATE_FORMAT));
        Period next = Period.between(start, end);
        return next.getDays();
    }

    public static List<String> getBetweenList(String startTime, String endTime){
        List<String> list = new ArrayList<>();
        LocalDate startDate = LocalDate.parse(startTime, DateTimeFormatter.ofPattern(DateUtils.DATE_FORMAT));
        LocalDate endDate = LocalDate.parse(endTime, DateTimeFormatter.ofPattern(DateUtils.DATE_FORMAT));
        long distance = ChronoUnit.DAYS.between(startDate, endDate);
        Stream.iterate(startDate, d -> d.plusDays(1)).limit(distance + 1).forEach(f -> list.add(f.toString()));
        return list;
    }

    public static List<String> getOneDay24Hours(String date){
        LocalDateTime startTime = LocalDateTime.parse(date + " 01:00:00", DateTimeFormatter.ofPattern(DateUtils.DEFAULT_DATE_TIME_FORMAT));
        LocalDateTime endTime = LocalDateTime.parse(date + " 00:00:00", DateTimeFormatter.ofPattern(DateUtils.DEFAULT_DATE_TIME_FORMAT)).plusDays(1);
        List<String> list = new ArrayList<>();
        LocalDateTime time = startTime;
        while (time.isBefore(endTime)) {
            list.add(getDateTimeAsString(time));
            time = time.plusHours(1);
        }
        list.add(date + " 24:00:00");
        return list;
    }

    public static String getDateAsString(LocalDateTime dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return dateTime.format(formatter);
    }

    public static String getDateAsString(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return date.format(formatter);
    }

    public static String getDateAsString(String format, LocalDateTime dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return dateTime.format(formatter);
    }

    public static String plusHours(String dateTime, int hours){
        return getDateTimeAsString(parseStringToDateTime(dateTime).plusHours(hours));
    }

    public static String minusHours(String dateTime, int hours){
        return getDateTimeAsString(parseStringToDateTime(dateTime).minusHours(hours));
    }

    public static LocalDate localDate(){
        return LocalDate.now();
    }

    public static LocalDate parseStringToLocalDate(String format, String date){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(date, df);
    }

    public static LocalDate parseStringToLocalDate(String date){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDate.parse(date, df);
    }

    public static LocalDateTime parseLocalDateTimeToLocalDate(LocalDate date){
        return LocalDateTime.of(date, LocalTime.MIN);
    }

    public static LocalDate parseLocalDateToLocalDateTime(LocalDateTime dateTime){
        return dateTime.toLocalDate();
    }

    public static String dayStartOfLocalDate(LocalDate date){
        LocalDateTime localDateTime = parseLocalDateTimeToLocalDate(date);
        return getDateTimeAsString(localDateTime.with(LocalTime.MIN));
    }

    public static String dayEndOfLocalDate(LocalDate date){
        LocalDateTime localDateTime = parseLocalDateTimeToLocalDate(date);
        return getDateTimeAsString(localDateTime.with(LocalTime.MAX));
    }


}
