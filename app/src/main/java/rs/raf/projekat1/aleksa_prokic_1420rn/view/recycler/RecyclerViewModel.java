package rs.raf.projekat1.aleksa_prokic_1420rn.view.recycler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RecyclerViewModel extends ViewModel {

    public static int counter = 101;

    private final MutableLiveData<List<DateCell>> dateCells = new MutableLiveData<>();
    private ArrayList<DateCell> dateCellList = new ArrayList<>();

    public RecyclerViewModel() {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        List<Date> dates = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            dates.add(today);
            calendar.add(Calendar.DAY_OF_MONTH, 1); // increment the date by one day
            today = calendar.getTime();
        }

        for (int i = 0; i < 10000; i++) {
            Date d = dates.get(i);
            calendar = Calendar.getInstance(); // set the Calendar's time to the given date
            calendar.setTime(d);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            DateCell dateCell = new DateCell(d,String.valueOf(dayOfMonth),i);
            dateCellList.add(dateCell);
        }

        // We are doing this because cars.setValue in the background is first checking if the reference on the object is same
        // and if it is it will not do notifyAll. By creating a new list, we get the new reference everytime
        ArrayList<DateCell> listToSubmit = new ArrayList<>(dateCellList);
        dateCells.setValue(listToSubmit);
    }

    public LiveData<List<DateCell>> getDateCells() {
        return dateCells;
    }

    public int addDateCell(Date date, String dayOfMonth) {
        int id = counter++;
        DateCell dateCell = new DateCell(date,dayOfMonth,id);
        dateCellList.add(dateCell);
        ArrayList<DateCell> listToSubmit = new ArrayList<>(dateCellList);
        dateCells.setValue(listToSubmit);
        return id;
    }

    public void removeCar(int id) {
        Optional<DateCell> carObject = dateCellList.stream().filter(dateCell -> dateCell.getId() == id).findFirst();
        if (carObject.isPresent()) {
            dateCellList.remove(carObject.get());
            ArrayList<DateCell> listToSubmit = new ArrayList<>(dateCellList);
            dateCells.setValue(listToSubmit);
        }
    }

}
