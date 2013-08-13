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
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class RestRouteBuilder extends RouteBuilder {
   @Override
   public void configure() throws Exception {
         from("cxfrs:http://0.0.0.0:9090?resourceClasses=cc.notsoclever.examples.CompanyService&bindingStyle=SimpleConsumer")
               .choice()
                   .when(header("operationName").isEqualTo("getCompany"))
                     .to("sql:SELECT * from company where id = :#id")
                   .when(header("operationName").isEqualTo("createCompany"))
                     .to("sql:INSERT INTO company(name, symbol) VALUES (:#name, :#symbol)")
                   .when(header("operationName").isEqualTo("getCompanies"))
                     .to("sql:select * from company")
                   .when(header("operationName").isEqualTo("updateCompany"))
                     .to("sql:UPDATE company SET name = :#name, symbol = :#symbol where id = :#id")
                   .when(header("operationName").isEqualTo("deleteCompany"))
                    .to("sql:DELETE FROM company where id = :#id")
               .end()
               .marshal().json(JsonLibrary.Jackson);
   }
}
