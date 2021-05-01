package com.looper_api.services;

import com.looper_api.models.binding.CsvInputDTO;

public interface CsvArrayListService {

    String convertToCSV(CsvInputDTO csvInputDTO);
}
