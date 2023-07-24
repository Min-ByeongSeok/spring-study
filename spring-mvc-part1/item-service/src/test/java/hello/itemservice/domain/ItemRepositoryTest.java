package hello.itemservice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    @DisplayName("상품 저장")
    void save() {
        // given
        Item item = new Item("itemA", 10000, 10);
        // when
        Item savedItem = itemRepository.save(item);
        Item findItem = itemRepository.findById(item.getId());
        // then
        Assertions.assertThat(savedItem).isEqualTo(findItem);
    }

    @Test
    @DisplayName("상품 조회")
    void findAll() {
        // given
        Item item1 = new Item("itemA", 10000, 10);
        Item item2 = new Item("itemB", 20000, 20);
        Item item3 = new Item("itemC", 30000, 30);
        Item item4 = new Item("itemD", 40000, 40);
        // when
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);
        itemRepository.save(item4);

        List<Item> all = itemRepository.findAll();
        // then
        Assertions.assertThat(all.size()).isEqualTo(4);
        Assertions.assertThat(all).contains(item1, item2, item3, item4);

    }

    @Test
    @DisplayName("상품 수정")
    void update() {
        // given
        Item item = new Item("itemA", 10000, 10);
        // when
        Item savedItem = itemRepository.save(item);
        Long id = savedItem.getId();
        Item updateParam = new Item("item22", 10, 10000);
        itemRepository.update(id, updateParam);
        // then
        Item findItem = itemRepository.findById(id);
        Assertions.assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
    }
}