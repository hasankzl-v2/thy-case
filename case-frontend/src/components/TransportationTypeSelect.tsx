import { FormControl, InputLabel, MenuItem, Select } from "@mui/material";
import { TransportationTypeEnum } from "../types/enum/TransportationTypeEnum";

interface TransportationTypeSelectProps {
  handleSelect: (type: TransportationTypeEnum | null) => void;
  selectedType: TransportationTypeEnum | null;
}

// select component for TransportationTypeEnum
function TransportationTypeSelect({
  handleSelect,
  selectedType,
}: TransportationTypeSelectProps) {
  return (
    <div>
      <FormControl fullWidth sx={{ minWidth: 150 }}>
        <InputLabel>Transport</InputLabel>
        <Select
          value={selectedType}
          onChange={(event) => {
            if (event.target.value) {
              handleSelect(event.target.value as TransportationTypeEnum);
            }
          }}
        >
          {Object.values(TransportationTypeEnum).map((option) => (
            <MenuItem key={option} value={option}>
              {option}
            </MenuItem>
          ))}
        </Select>
      </FormControl>
    </div>
  );
}

export default TransportationTypeSelect;
