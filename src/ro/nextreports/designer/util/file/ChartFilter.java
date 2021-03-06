/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ro.nextreports.designer.util.file;

import ro.nextreports.designer.util.I18NSupport;

/**
 * User: mihai.panaitescu
 * Date: 18-Dec-2009
 * Time: 13:59:59
 */
public class ChartFilter extends ExtensionFilter {

    public static final String CHART_EXTENSION = ".chart";

    public ChartFilter() {
    	super(CHART_EXTENSION);
    }

    @Override
    public String getDescription() {
        return I18NSupport.getString("next.file.filter");
    }

}
