package com.yassine.insta_clone_backend.utils;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

@Service
public class TimeUtils {

    public String getTimeBetweenDates(LocalDateTime dateTime1, LocalDateTime dateTime2){
        Period period = Period.between(dateTime1.toLocalDate(), dateTime2.toLocalDate());
        Duration duration = Duration.between(dateTime1, dateTime2);
        if (period.getYears() > 0){
            return (period.getYears() == 1) ? (period.getYears() + " year ago") : (period.getYears() + " years ago");
        } else if (period.getMonths() > 0) {
            return (period.getMonths() == 1) ? (period.getMonths() + " month ago") : (period.getMonths() + " months ago");
        } else if (period.getDays() > 0) {
            return getPeriodDependingOnDays(period.getDays());
        }
        long hours = duration.toHours();
        long minutes = duration.toMinutes();
        if (hours > 0){
            return (hours == 1) ? (hours + " hour ago") : (hours + " hours ago");
        } else if (minutes > 0) {
            return (minutes == 1) ? (minutes + " minute ago") : (minutes + " minutes ago");
        } else if (duration.getSeconds() > 0) {
            return (duration.getSeconds() == 1) ? (duration.getSeconds() + " second ago") : (duration.getSeconds() + " seconds ago");
        }
        return null;
    }

    public String getPeriodDependingOnDays(int days){
        if (days == 1){
            return (days + " day ago");
        } else if (days > 6 && days < 15) {
            return ("1 week ago");
        } else if (days > 15 && days < 21) {
            return ("2 weeks ago");
        } else if (days > 21 && days < 30) {
            return ("3 weeks ago");
        }
        return (days + " days ago");
    }
}
