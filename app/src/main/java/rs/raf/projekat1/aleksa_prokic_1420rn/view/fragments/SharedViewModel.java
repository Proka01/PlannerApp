package rs.raf.projekat1.aleksa_prokic_1420rn.view.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import rs.raf.projekat1.aleksa_prokic_1420rn.view.recyclerCalendar.DateCell;

public class SharedViewModel extends ViewModel{
    private final MutableLiveData<DateCell> dateCellValue = new MutableLiveData<>();

    public LiveData<DateCell> getDateCellValue() {
        return dateCellValue;
    }

    public void storeDateCellValue(DateCell dateCell) {
        dateCellValue.setValue(dateCell);
    }
}
