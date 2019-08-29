package com.saffetturkoglu.app;

import com.saffetturkoglu.app.exception.GameException;
import com.saffetturkoglu.app.model.Player;
import com.saffetturkoglu.app.service.CardService;
import com.saffetturkoglu.app.service.GameService;

import static com.saffetturkoglu.app.Util.Constants.PLAYER_ONE_NAME;
import static com.saffetturkoglu.app.Util.Constants.PLAYER_TWO_NAME;

public class Game {
    public static void main(String [] args)
    {
        GameService gameService = new GameService(new Player(PLAYER_ONE_NAME),new Player(PLAYER_TWO_NAME),new CardService());
        if(!gameService.run()){
            throw new GameException("Failed Start The Game.");
        }
    }
}
