import * as React from "react";
import { DemoContainer } from "@mui/x-date-pickers/internals/demo";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";

interface DatePickerComponentProps {
  handleSelect: (date: string) => void;
}
export default function DatePickerComponent({
  handleSelect,
}: DatePickerComponentProps) {
  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <DatePicker
        label="Operation Date"
        onChange={(date) => {
          if (date) {
            const formattedDate = date.format("DD/MM/YYYY");
            handleSelect(formattedDate);
          }
        }}
      />
    </LocalizationProvider>
  );
}
