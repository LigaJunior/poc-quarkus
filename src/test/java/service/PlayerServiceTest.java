package service;

import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import model.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.PlayerRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@QuarkusTest
public class PlayerServiceTest {

    private PlayerService service;
    private MockPlayerRepository mockPlayerRepository;

    Player unallocated;
    Player p2;
    Player p3;
    List<Player> defaultPlayerList;

    @Before
    public void init() {
        unallocated = new Player("João");
        p2 = new Player("José");
        p3 = new Player("Maria");
        defaultPlayerList = new ArrayList<>(Arrays.asList(unallocated, p2, p3));
        mockPlayerRepository = new MockPlayerRepository();
        service = new PlayerService(this.mockPlayerRepository);
    }

    @Test
    public void findAllShouldReturnAllPlayers() {
        List source = this.service.findAll();
        Assert.assertEquals(defaultPlayerList, source);
    }

    @Mock
    @ApplicationScoped
    private class MockPlayerRepository extends PlayerRepository {
        public MockPlayerRepository() {
            super(null);
        }

        @Override
        public List<Player> findAll() {
            return defaultPlayerList;
        }

        @Override
        public List<Player> findById(Long playerId) {
            return defaultPlayerList.stream().filter(player -> player.getId().equals(playerId)).collect(Collectors.toList());
        }

        @Override
        public void persist(Player player) {
        }

        @Override
        public void merge(Player targetPlayer) {
        }

        @Override
        public List findUnallocated() {
            return Arrays.asList(unallocated);
        }

        @Override
        public List findPlayerListWithConsumption() {
            return Arrays.asList(
                    new Object[]{1, "2019-10-07", "João", true, 10},
                    new Object[]{1, "2019-10-07", "José", true, 7},
                    new Object[]{1, "2019-10-07", "Maria", true, 8}
            );
        }
    }
}

