package com.codemaniac.messagingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "sent_messages")
@Getter
@Setter
public class SentMessage extends Message{
    private LocalDateTime sentAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SentMessage)) return false;
        if (!super.equals(o)) return false;
        SentMessage that = (SentMessage) o;
        return sentAt.equals(that.sentAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sentAt);
    }
}
