package com.tabisketch.mapper;

import com.tabisketch.bean.entity.Place;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalTime;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlacesMapperTest {
    @Autowired
    private IPlacesMapper placesMapper;

    @Test
    @Sql({
            "classpath:/sql/CreateUser.sql",
            "classpath:/sql/CreatePlan.sql",
            "classpath:/sql/CreateDay.sql",
            "classpath:/sql/CreateGooglePlace.sql",
    })
    public void testInsert() {
        final var place = new Place(
                -1,
                1,
                1,
                0,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                null,
                null,
                null,
                null,
                null
        );
        this.placesMapper.insert(place);
        assert place.getId() != -1;
    }

    @Test
    @Sql({
            "classpath:/sql/CreateUser.sql",
            "classpath:/sql/CreatePlan.sql",
            "classpath:/sql/CreateDay.sql",
            "classpath:/sql/CreateGooglePlace.sql",
            "classpath:/sql/CreatePlace.sql"
    })
    public void testSelect() {
        final int dayId = 1;
        final var placeList = this.placesMapper.selectByDayId(dayId);
        assert placeList != null;
        assert !placeList.isEmpty();
    }
}
