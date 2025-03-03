import React, { useEffect, useState } from "react";
import { Autocomplete, TextField, Chip } from "@mui/material";
import { daysOfWeek } from "../Constants";

interface MultiSelectDaysProps {
  handleSelect: (selectedDays: Array<number>) => void;
  selectedOperationDays: Array<number> | null;
}
const MultiSelectDays = ({
  handleSelect,
  selectedOperationDays,
}: MultiSelectDaysProps) => {
  const [selectedDays, setSelectedDays] = useState([]);

  useEffect(() => {
    if (selectedOperationDays == null) {
      setSelectedDays([]);
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
