package com.looper_api.services.impl;

import com.google.gson.Gson;
import com.looper_api.data_structures.LooperArrayList;
import com.looper_api.data_structures.impl.LooperArrayListImpl;
import com.looper_api.models.binding.data_structures.CsvExportDTO;
import com.looper_api.models.binding.data_structures.CsvImportDTO;
import com.looper_api.services.CsvArrayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CsvArrayListServiceImpl implements CsvArrayListService {

    private final Gson gson;

    @Autowired
    public CsvArrayListServiceImpl(Gson gson) {
        this.gson = gson;
    }

    @Override
    public String convertToCSV(CsvImportDTO csvImportDTO) {

        LooperArrayList looperArrayList = new LooperArrayListImpl(csvImportDTO.getNumbers());

        /* Convert input numbers array to CSV string */
        String csvString = looperArrayList.convertToCSV();

        /* Export CSV result as JSON */
        CsvExportDTO csvExportDTO =  new CsvExportDTO(csvString);
        String csvJSON = this.gson.toJson(csvExportDTO);

        return csvJSON;
    }
}
