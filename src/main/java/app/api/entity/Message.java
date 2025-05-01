package app.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "messages")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(MessageId.class)
public class Message {
  @Id
  @Column(name = "chat_id", nullable = false)
  private Long chatId;

  @Id
  @Column(name = "message_id", nullable = false)
  private int messageId;

  @Column(name = "is_deleted", nullable = false)
  private boolean isDeleted;

  @Column(name = "is_have_inline_keyboard", nullable = false)
  private boolean isHaveInlineKeyboard;

  @Column(name = "is_have_reply_keyboard", nullable = false)
  private boolean isHaveReplyKeyboard;
}