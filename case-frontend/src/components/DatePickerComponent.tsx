import React, { useEffect, useState } from "react";
import { DemoContainer } from "@mui/x-date-pickers/internals/demo";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { Dayjs } from "dayjs";

interface DatePickerComponentProps {
  handleSelect: (date: string) => void;
  selectedDate: string;
}
export default function DatePickerComponent({
  handleSelect,
  selectedDate,
}: DatePickerComponentProps) {
  const [value, setValue] = useState<Dayjs | null>(null);

  useEffect(() => {
    if (selectedDate == null || selectedDate == "") {
      setValue(null);
    }
  }, [selectedDate]);
  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <DatePicker
        label="Operation Date"
        value={value}
        onChange={(date) => {
          setValue(date);
          if (date) {
            const formattedDate = date.format("DD/MM/YYYY");
            handleSelect(formattedDate);
          }
        }}
      />
    </LocalizationProvider>
  );
}
