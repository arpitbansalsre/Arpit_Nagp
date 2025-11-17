package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.AddressBook;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonUtil {

    public List<AddressBook> getAddressJsonData(String jsonfilepath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(jsonfilepath), new TypeReference<List<AddressBook>>() {
        });
    }
}
