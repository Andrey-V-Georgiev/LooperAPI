package com.looper_api.services;

import com.looper_api.data_structures.LooperArrayList;
import com.looper_api.models.binding.loopers.LooperExportDTO;
import com.looper_api.models.binding.loopers.LooperImportDTO;

public interface LooperService {

    LooperArrayList<LooperExportDTO> findAllLoopersDetails();

    LooperExportDTO startNewLooper(LooperImportDTO looperImportDTO) throws InterruptedException;

    LooperExportDTO killLooper(Long threadId) throws InterruptedException;

    LooperArrayList<LooperExportDTO> killAllLoopers() throws InterruptedException;

}
