/******************************************************************************
 * Copyright (C) 2010-2016 CERN. All rights not expressly granted are reserved.
 *
 * This file is part of the CERN Control and Monitoring Platform 'C2MON'.
 * C2MON is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the license.
 *
 * C2MON is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with C2MON. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************************/

package cern.c2mon.server.shorttermlog.structure;

import cern.c2mon.server.common.control.ControlTag;
import cern.c2mon.server.common.datatag.DataTag;
import cern.c2mon.server.common.tag.Tag;
import cern.c2mon.shared.common.datatag.TagQualityStatus;
import cern.c2mon.shared.common.type.TypeConverter;
import cern.c2mon.shared.util.json.GsonFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

/**
 *This class is in charge of all objects transformations that may involved the
 * DataTagShortTermLog class It is aware of the DataShortTermLog java bean
 * structure and knows how its information has to be transfered into/from other
 * objects
 *
 * @author mruizgar
 *
 */
@Slf4j
public final class DataTagShortTermLogConverter implements LoggerConverter<Tag> {

    /** The maximum length for the reportText */
    private static final int MAX_LENGTH = 1000;

    /** The index to which the text will be significative */
    private static final int SPLIT_INDEX = 99;

    /**
     * Jackson object used for converting DataTagQuality to String.
     */
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Creates a DataTagValue object from the info stored in the
     * DataTagCacheObject
     *
     * @param pTag
     *            DataTagCacheObject containing some useful metadata for the
     *            DataTagValue object
     * @param dtShortTerm
     *            DataTagShortTermLog object containing some useful metadata for the
     *            DataTagValue object
     * @return An object containing the dataTags values previously stored in the
     *         DataTagValueResult
     */

//    public static DataTagValue dataTagShorTermLogToDataTagValue(
//            final DataTagShortTermLog dtShortTerm, final DataTagCacheObject pTag) {
//        DataTagValueImpl dtValue = new DataTagValueImpl();
//
//        dtValue.setId(pTag.getId());
//        dtValue.setName(pTag.getName());
//        dtValue.setDescription(pTag.getDescription());
//        dtValue.setDataType(dtShortTerm.getTagDataType());
//        dtValue.setValue(toTagValue(dtShortTerm));
//        dtValue.setValueDescription(toValueDescription(pTag.getValueDictionary(), dtShortTerm,
//                dtValue.getValue()));
//        // dtValue.setTimestamp(Timestamp.valueOf(this.getTagTimestamp()));
//        dtValue.setTimestamp(dtShortTerm.getTagTimestamp());
//        dtValue.setQuality(toQuality(dtShortTerm.getTagQualityCode(), dtShortTerm
//                .getTagQualityDesc()));
//        dtValue.setMode(dtShortTerm.getTagMode());
//        dtValue.setSimulated(false);
//
//        return dtValue;
//    }

    /**
     * Creates a new DataTagQuality object depending on the values passed as
     * parameters
     *
     * @param code
     *            Number that indicates the quality of the datatag
     * @param desc
     *            Description of the code number
     * @return A new DataTagQuality object
     */
//    private static DataTagQuality toQuality(final short code, final String desc) {
//        DataTagQuality dtQuality = new DataTagQuality();
//        if (code == DataTagQuality.OK) {
//            dtQuality = new DataTagQuality(DataTagQuality.OK, "OK");
//        } else {
//            dtQuality = new DataTagQuality(code, desc);
//        }
//        return dtQuality;
//    }

    /**
     * Creates a new object whose type is set up based in the parameters
     *
     * @param dtShortTerm
     *            DataTagShortTermLog object containing the value and type
     *            to which a new object has to be created
     *
     * @return An object of the same type and value as indicated per the
     *         parameters
     */
    private static Object toTagValue(final TagShortTermLog dtShortTerm) {
        Object tagValue = null;
        if (dtShortTerm.getTagValue() != null) {
            tagValue = TypeConverter.cast(dtShortTerm.getTagValue(), dtShortTerm
                    .getTagDataType());
        }
        return tagValue;
    }

    /**
     * Assigns a description to the DataTag depending on the values of its other
     * attributes
     *
     * @param dtDictionary
     *            Object containing several descriptions that are used for the
     *            datatags
     * @param dtShortTerm
     *            Object containing useful information for the dataTag
     * @param dtValue
     *            The dataTag value object
     * @return A complete description for the dataTag
     */
//    private static String toValueDescription(final DataTagValueDictionary dtDictionary,
//            final TagShortTermLog dtShortTerm, final Object dtValue) {
//        String valueDescription = "";
//        if (dtShortTerm.getTagQualityCode() == DataTagQuality.OK) {
//            if (dtShortTerm.getTagName() != null) {
//                valueDescription = dtShortTerm.getTagQualityDesc();
//            } else {
//                valueDescription = "";
//            }
//        }
//        if (dtValue != null) {
//            if (dtDictionary != null && (dtDictionary.getDescription(dtValue) != null)) {
//                valueDescription = valueDescription + " "
//                        + dtDictionary.getDescription(dtValue);
//            }
//        }
//        return valueDescription;
//    }

    @Override
    public Loggable convertToLogged(Tag tag) {
   // create an empty DataTagShortTermLog object
      TagShortTermLog dtSTLog = new TagShortTermLog();
      // Populate its fields with data from the object received as a parameter
      dtSTLog.setTagId(tag.getId().longValue());
      dtSTLog.setTagName(tag.getName());

      if (tag.getValue() != null) {
        try {

          dtSTLog.setTagValue(mapper.writeValueAsString(tag.getValue()));

        } catch (JsonProcessingException e) {
          log.error("Could nor parse the invalid states of the tag "+tag.getId()+" into a String.", e);
        }
      } else {

          dtSTLog.setTagValue(null);
      }

      dtSTLog.setTagValueDesc(tag.getValueDescription());
      dtSTLog.setTagDataType(tag.getDataType());

      if (tag instanceof DataTag || tag instanceof ControlTag) {
        dtSTLog.setSourceTimestamp(((DataTag) tag).getSourceTimestamp());
        dtSTLog.setDaqTimestamp(((DataTag) tag).getDaqTimestamp());
      }

      dtSTLog.setServerTimestamp(tag.getCacheTimestamp());

      int code = 0;
      if (tag.getDataTagQuality() != null) {
        for (TagQualityStatus status : tag.getDataTagQuality().getInvalidQualityStates().keySet()) {
          code = (int) (code + Math.pow(2, status.getCode()));
        }
      }

      dtSTLog.setTagQualityCode(code); //for longterm log and statistics purpose

      try {
        dtSTLog.setTagQualityDesc(mapper.writeValueAsString(tag.getDataTagQuality().getInvalidQualityStates()));
      } catch (JsonProcessingException e) {
        log.error("Could nor parse the invalid states of the tag "+tag.getId()+" into a String.", e);
      }

      if (dtSTLog.getTagQualityDesc() != null && dtSTLog.getTagQualityDesc().length() > MAX_LENGTH) {
          dtSTLog.setTagQualityDesc("{\"UNKNOWN_REASON\":\"Invalid quality String was too long: unable to store in ShortTermLog table.\"}");
      }

      dtSTLog.setTagDir("I");
      dtSTLog.setTagMode(tag.getMode());
      return dtSTLog;
    }

}