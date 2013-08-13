package cc.notsoclever.examples;
/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class DatabaseBean {

   private static final Logger LOG = LoggerFactory.getLogger(DatabaseBean.class);
   private DataSource dataSource;

   public DataSource getDataSource() {
      return dataSource;
   }

   public void setDataSource(DataSource dataSource) {
      this.dataSource = dataSource;
   }

   public void create() throws Exception {
      JdbcTemplate jdbc = new JdbcTemplate(dataSource);

      String sql = "create table company (\n"
            + "  ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n"
            + "  name varchar(30),\n"
            + "  symbol varchar(10)\n"
            + ")";

      LOG.info("Creating table company ...");

      try {
         jdbc.execute("drop table company");
      } catch (Throwable e) {
         // ignore
      }

      jdbc.execute(sql);

      LOG.info("... created table company");
   }

   public void destroy() throws Exception {
      JdbcTemplate jdbc = new JdbcTemplate(dataSource);

      try {
         jdbc.execute("drop table company");
      } catch (Throwable e) {
         // ignore
      }
   }
}
