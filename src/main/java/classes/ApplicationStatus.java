package classes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Тёма on 13.04.2017.
 */
public enum ApplicationStatus {
    /**
     * заявка подана
     */
    ACTIVE(1),

    /**
     * Отменена пользователем
     */
    CLIENT_CANCEL(2),

    /**
     * Отказано
     */
    REFUSED(3),

    /**
     * Приянята оформляется
     */
    APPROVE(4),
    /**
     * Просмотрена банком
     */
    WATCHED(5),
    /**
     * Реализовано
     */
    SELL(6);

    private int status;

    ApplicationStatus(int status) {
        this.status = status;
    }

    public static ApplicationStatus valueOf(int code) {
        for (ApplicationStatus status : values()) {
            if (status.status == code) return status;
        }
        return null;
    }

    public boolean in(ApplicationStatus... statuses) {
        for (ApplicationStatus status : statuses) {
            if (this == status) return true;
        }
        return false;
    }

    public int getStatus() {
        return status;
    }

}