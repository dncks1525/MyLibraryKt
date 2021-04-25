/**
 * Copyright 2021 Lee Woochan <dncks1525@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chani.mylibrarykt.data.model

import com.google.gson.annotations.SerializedName

data class Pdf(
    @SerializedName("Chapter 1") val chapter1: String?,
    @SerializedName("Chapter 2") val chapter2: String?,
    @SerializedName("Chapter 3") val chapter3: String?,
    @SerializedName("Chapter 4") val chapter4: String?,
    @SerializedName("Chapter 5") val chapter5: String?,
)