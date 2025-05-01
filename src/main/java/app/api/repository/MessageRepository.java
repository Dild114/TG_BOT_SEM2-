package app.api.repository;

import app.api.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import java.util.*;

public interface MessageRepository extends JpaRepository<Message, MessageId> {
  List<Message> findByChatId(Long chatId);

  @Query("""
    SELECT m.messageId FROM Message m
    WHERE m.chatId = :chatId AND m.isHaveReplyKeyboard = true
    """)
  int findByChatIdAndisHaveReplyKeyboardTrue(@Param("chatId") Long chatId);

  @Query("""
  SELECT m.messageId FROM Message m
  WHERE m.chatId = :chatId AND m.isHaveInlineKeyboard = true
  """)
  Integer findByChatIdAndHaveInlineKeyboardTrue(@Param("chatId") Long chatId);

  List<Message> findByChatIdAndIsDeletedTrue(long chatId);

  List<Message> findByChatIdAndIsDeletedFalse(long chatId);

  @Modifying
  @Query("""
    DELETE Message m
    Where m.chatId = :chatId AND m.messageId = :messageId
    """)
  void deleteMessage(@Param("chatId") Long chatId, @Param("messageId") int messageId);
}