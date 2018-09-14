package de.seven.converter.model;

import de.seven.converter.enums.StateType;
import de.seven.converter.json.Object2JsonConverter;
import de.seven.fate.model.builder.annotation.Inject;
import de.seven.fate.model.builder.util.FieldInjectionUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class WaitStateDefinitionTest {

    @Inject
    WaitStateDefinition sut;

    @Inject
    Object2JsonConverter object2JsonConverter;

    @Before
    public void setUp() {
        FieldInjectionUtil.injectFields(this);
    }

    @Test
    public void taskType() {

        assertSame(StateType.WAIT, sut.getType());
    }

    @Test
    public void waitTenSeconds() {

        sut.setNext("NextState");
        sut.setSeconds(10);

        String expected = "{\n" +
                "  \"Type\" : \"Wait\",\n" +
                "  \"Seconds\" : 10,\n" +
                "  \"Next\" : \"NextState\"\n" +
                "}";

        assertEquals(expected, object2JsonConverter.convert(sut));
    }

    @Test
    public void waitUntil() throws Exception {

        sut.setNext("NextState");
        sut.setTimestamp(DateUtils.parseDate("2016-03-14T01:59:00Z", "YYYY-MM-DD'T'hh:mm:ss'Z'"));

        String expected = "{\n" +
                "  \"Type\" : \"Wait\",\n" +
                "  \"Timestamp\" : \"2016-01-04T00:59:00Z\",\n" +
                "  \"Next\" : \"NextState\"\n" +
                "}";

        assertEquals(expected, object2JsonConverter.convert(sut));
    }

    @Test
    public void waitUntilTimestampPath() {

        sut.setNext("NextState");
        sut.setTimestampPath("$.expirydate");

        String expected = "{\n" +
                "  \"Type\" : \"Wait\",\n" +
                "  \"TimestampPath\" : \"$.expirydate\",\n" +
                "  \"Next\" : \"NextState\"\n" +
                "}";

        assertEquals(expected, object2JsonConverter.convert(sut));
    }
}