/*******************************************************************************
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
 ******************************************************************************/

package cern.c2mon.server.elasticsearch.tag.config;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cern.c2mon.server.elasticsearch.Indices;
import cern.c2mon.server.elasticsearch.MappingFactory;
import cern.c2mon.server.elasticsearch.client.ElasticsearchClient;
import cern.c2mon.server.elasticsearch.config.ElasticsearchProperties;

/**
 * This class manages the indexing of {@link TagConfigDocument} instances to
 * the Elasticsearch cluster.
 *
 * @author Szymon Halastra
 * @author Justin Lewis Salmon
 */
@Slf4j
@Component
public class TagConfigDocumentIndexer {

  private static final String TYPE = "tag_config";

  private final ElasticsearchClient client;

  private final String configIndex;

  @Autowired
  public TagConfigDocumentIndexer(final ElasticsearchClient client, final ElasticsearchProperties properties) {
    this.client = client;
    this.configIndex = properties.getTagConfigIndex();
  }

  public void indexTagConfig(TagConfigDocument tag) {
    if (!Indices.exists(configIndex)) {
      Indices.create(configIndex, TYPE, MappingFactory.createTagConfigMapping());
    }

    IndexRequest indexNewTag = new IndexRequest(configIndex, TYPE,
            String.valueOf(tag.getId())).source(tag.toString()).routing(tag.getId());

    try {
      client.getClient().index(indexNewTag).get();
      client.waitForYellowStatus();
    } catch (Exception e) {
      log.error("Error occurred while indexing the config for tag #{}", tag.getId(), e);
    }
  }

  public void updateTagConfig(TagConfigDocument tag) {
    UpdateRequest updateRequest = new UpdateRequest(configIndex, TYPE,
            String.valueOf(tag.getId())).doc(tag.toString()).routing(tag.getId());

    try {
      client.getClient().update(updateRequest).get();
      client.waitForYellowStatus();
    } catch (Exception e) {
      log.error("Error occurred while updating the config for tag #{}", tag.getId(), e);
    }
  }

  public void removeTagConfig(TagConfigDocument tag) {
    DeleteRequest deleteRequest = new DeleteRequest(configIndex, TYPE,
            String.valueOf(tag.getId())).routing(tag.getId());

    try {
      client.getClient().delete(deleteRequest).get();
      client.waitForYellowStatus();
    } catch (Exception e) {
      log.error("Error occurred while deleting the config for tag #{}", tag.getId(), e);
    }
  }
}
