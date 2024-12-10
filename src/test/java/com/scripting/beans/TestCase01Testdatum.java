
package com.scripting.beans;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "googleURL",
    "searchTerm"
})
public class TestCase01Testdatum {

    @JsonProperty("googleURL")
    private String googleURL;
    @JsonProperty("searchTerm")
    private String searchTerm;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("googleURL")
    public String getGoogleURL() {
        return googleURL;
    }

    @JsonProperty("googleURL")
    public void setGoogleURL(String googleURL) {
        this.googleURL = googleURL;
    }

    public TestCase01Testdatum withGoogleURL(String googleURL) {
        this.googleURL = googleURL;
        return this;
    }

    @JsonProperty("searchTerm")
    public String getSearchTerm() {
        return searchTerm;
    }

    @JsonProperty("searchTerm")
    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public TestCase01Testdatum withSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public TestCase01Testdatum withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(TestCase01Testdatum.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("googleURL");
        sb.append('=');
        sb.append(((this.googleURL == null)?"<null>":this.googleURL));
        sb.append(',');
        sb.append("searchTerm");
        sb.append('=');
        sb.append(((this.searchTerm == null)?"<null>":this.searchTerm));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.searchTerm == null)? 0 :this.searchTerm.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.googleURL == null)? 0 :this.googleURL.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TestCase01Testdatum) == false) {
            return false;
        }
        TestCase01Testdatum rhs = ((TestCase01Testdatum) other);
        return ((((this.searchTerm == rhs.searchTerm)||((this.searchTerm!= null)&&this.searchTerm.equals(rhs.searchTerm)))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.googleURL == rhs.googleURL)||((this.googleURL!= null)&&this.googleURL.equals(rhs.googleURL))));
    }

}
