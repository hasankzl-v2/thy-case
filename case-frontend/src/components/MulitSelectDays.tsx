import React, { useState } from "react";
import { Autocomplete, TextField, Chip } from "@mui/material";
import { daysOfWeek } from "../Constants";

interface MultiSelectDaysProps {
  handleSelect: (selectedDays: Array<number>) => void;
}
const MultiSelectDays = ({ handleSelect }: MultiSelectDaysProps) => {
  const [selectedDays, setSelectedDays] = useState([]);

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
            <Chip
              key={option.value}
              label={option.label}
              {...getTagProps({ index })}
            />
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
