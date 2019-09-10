package model.ViewModel;

import model.Player;
import model.Sprint;

import java.util.ArrayList;
import java.util.List;

public class VMConverter {
    public static SprintVM convertSprint(Sprint sprint){
        SprintVM convertedSprint = new SprintVM(sprint.getId(),sprint.getName(),sprint.getActive(),sprint.getStartDate(),
                sprint.getEndDate(),sprint.getSprintNumber(),sprint.getRegistrationDate());
        List<PlayerVM> playersVM = new ArrayList<>();
        sprint.getPlayers().forEach(p-> playersVM.add(convertPlayer(p)));
        convertedSprint.setPlayers(playersVM);
        return convertedSprint;
    }
    public static List<SprintVM> convertSprints(List<Sprint> source){
        List<SprintVM> sprints = new ArrayList<>();
        source.forEach(s->sprints.add(convertSprint(s)));
        return sprints;
    }
    public static PlayerVM convertPlayer(Player player){
        return new PlayerVM(player.getId(),player.getName(),player.getRegistrationDate());
    }
    public static List<PlayerVM> convertPlayers(List<Player> source){
        List<PlayerVM> convertedPlayers = new ArrayList<>();
        source.forEach(p->convertedPlayers.add(convertPlayer(p)));
        return convertedPlayers;
    }
}
