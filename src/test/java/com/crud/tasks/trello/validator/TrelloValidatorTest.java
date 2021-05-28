package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TrelloValidatorTest {

    @InjectMocks
    private final TrelloValidator trelloValidator = new TrelloValidator();

    @Test
    public void testValidateTrelloBoards() {

        //Given
        TrelloBoard firstBoardStub = new TrelloBoard("0", "test", new ArrayList<>());
        TrelloBoard secondBoardStub = new TrelloBoard("1", "testName", new ArrayList<>());
        List<TrelloBoard> trelloBoardsStub = new ArrayList<>();
        trelloBoardsStub.add(firstBoardStub);
        trelloBoardsStub.add(secondBoardStub);

        //When
        List<TrelloBoard> validatedTrelloBoards = trelloValidator.validateTrelloBoards(trelloBoardsStub);

        List<TrelloBoard> validatedEmptyTrelloBoards = trelloValidator.validateTrelloBoards(new ArrayList<>());

        //Then
        assertEquals(1, validatedTrelloBoards.size());
        assertEquals("testName", validatedTrelloBoards.get(0).getName());
        assertEquals(0, validatedEmptyTrelloBoards.size());
    }

    @Test
    public void testValidateTrelloCard() {

        //Given
        TrelloValidator trelloValidator = new TrelloValidator();
        TrelloCard trelloCard = new TrelloCard("test", "test", "top", "1");

        //Then
        trelloValidator.validateCard(trelloCard);
    }
}
