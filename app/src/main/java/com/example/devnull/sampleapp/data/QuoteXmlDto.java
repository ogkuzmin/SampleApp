package com.example.devnull.sampleapp.data;

import com.example.devnull.sampleapp.domain.QuoteEntity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Data transfer object received from XML parsing.
 */
@Root(name="quote", strict = false)
public class QuoteXmlDto {

    @Element(name="id")
    long id;

    @Element(name="date")
    String date;

    @Element(name="text")
    String text;

    public static QuoteEntity createQuoteEntityFromDto(QuoteXmlDto dto) {
        QuoteEntity entity = new QuoteEntity();
        entity.setId(dto.id);
        entity.setDate(dto.date);
        entity.setText(dto.text);

        return entity;
    }
}
