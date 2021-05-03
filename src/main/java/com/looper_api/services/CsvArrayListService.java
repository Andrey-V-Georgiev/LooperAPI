package com.looper_api.services;

import com.looper_api.models.binding.data_structures.CsvImportDTO;

public interface CsvArrayListService {

    String convertToCSV(CsvImportDTO csvImportDTO);
}
