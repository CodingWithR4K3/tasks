package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private AdminConfig adminConfig;

    @Test
    public void testFetchTrelloBoards() {

        //Given
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(new TrelloListDto("1", "test", false));
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(new TrelloBoardDto("1", "test", trelloListDtos));
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtos);

        //When
        List<TrelloBoardDto> testList = trelloService.fetchTrelloBoards();

        //Then
        assertEquals("test", trelloBoardDtos.get(0).getName());
        assertEquals(1, trelloBoardDtos.size());
    }

    @Test
    public void testFetchEmptyTrelloBoards() {

        //Given
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtos);

        //When
        List<TrelloBoardDto> testList = trelloService.fetchTrelloBoards();

        //Then
        assertEquals(0, testList.size());
    }

    @Test
    public void testCreateTrelloCard() {

        //Given
        Trello trello = new Trello(1, 1);
        AttachmentsByType attachmentsByType = new AttachmentsByType(trello);
        Badges badges = new Badges(1, attachmentsByType);
        TrelloCardDto trelloCardDto = new TrelloCardDto("test", "test", "top", "1");
        CreatedTrelloCardDto createdTrelloCard = new CreatedTrelloCardDto("1", "test", "http://test.com", badges);
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCard);
        when(adminConfig.getAdminMail()).thenReturn("test@test.com");

        //When
        CreatedTrelloCardDto newCard = trelloService.createTrelloCard(trelloCardDto);

        //Then
        assertEquals("1", newCard.getId());
        assertEquals("test", newCard.getName());
        assertEquals("http://test.com", newCard.getShortUrl());
    }
}