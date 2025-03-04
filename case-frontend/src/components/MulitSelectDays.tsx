import React, { useEffect, useState } from "react";
import { Autocomplete, TextField, Chip } from "@mui/material";
import { daysOfWeek } from "../Constants";

interface MultiSelectDaysProps {
  handleSelect: (selectedDays: Array<number>) => void;
  selectedOperationDays: Array<number> | null;
}
// select days compoment for operation days
const MultiSelectDays = ({
  handleSelect,
  selectedOperationDays,
}: MultiSelectDaysProps) => {
  const [selectedDays, setSelectedDays] = useState<any>([]);

  // set empty array for selected days when prop is null and form cleared
  useEffect(() => {
    if (selectedOperationDays == null) {
      setSelectedDays([]);
    } else {
      // set selected days by matching operation days
      setSelectedDays(
        selectedOperationDays.map((d) =>
          daysOfWeek.find((dow) => dow.value == d)
        )
      );
    }
  }, [selectedOperationDays]);
  return (
    <div>
      <Autocomplete
        multiple
        options={daysOfWeek}
        getOptionLabel={(option) => option.label}
        value={selectedDays}
        onChange={(event, newValue) => {
          setSelectedDays(newValue);
          handleSelect(newValue.map((v) => v.value));
        }}
        renderTags={(value, getTagProps) =>
          value.map((option, index) => (
            <Chip label={option.label} {...getTagProps({ index })} />
          ))
        }
        renderInput={(params) => (
          <TextField
            {...params}
            label="Select operation days"
            placeholder="Select operation days"
          />
        )}
        fullWidth
        sx={{ minWidth: 400 }}
      />
    </div>
  );
};

export default MultiSelectDays;
