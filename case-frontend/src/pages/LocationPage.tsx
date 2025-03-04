import { useState, useEffect } from "react";
import ILocationData from "../types/ILocationData";
import LocationService from "../service/LocationService";
import Paper from "@mui/material/Paper";
import { DataGrid, GridColDef } from "@mui/x-data-grid";
import { Edit as EditIcon, Delete as DeleteIcon } from "@mui/icons-material";
import { Box, IconButton, TextField } from "@mui/material";
import ConfirmDialog from "../components/ConfirmationDialog";
import { toast } from "react-toastify";
import LocationModal from "../components/LocationSaveModal";
import LocationUpdateModal from "../components/LocationUpdateModal";
import IErrorResponse from "../types/response/IErrorResponse";
import ToastComponents from "../components/ToastComponents";
import { emptyLocation } from "../Constants";
import ButtonComponent from "../components/ButtonComponent";
import SearchIcon from "@mui/icons-material/Search";
import ClearIcon from "@mui/icons-material/Clear";

function LocationPage() {
  const [data, setData] = useState<ILocationData[]>([]);
  const [openDialog, setOpenDialog] = useState(false); // diolog open state
  const [selectedData, setSelectedData] = useState<ILocationData | null>(); // column selected Location
  const [page, setPage] = useState<number>(0);
  const [pageSize, setPageSize] = useState<number>(10);
  const [totalRows, setTotalRows] = useState<number>(0);
  const [filters, setFilters] = useState<ILocationData>(emptyLocation);
  const [updateData, setUpdateData] = useState<ILocationData>(emptyLocation);
  const [openUpdateModal, setOpenUpdateModal] = useState(false);

  // fetch data after page or pageSize change
  useEffect(() => {
    fetchData();
  }, [page, pageSize]);

  // column definations
  const columns: GridColDef[] = [
    { field: "id", headerName: "ID", width: 130, filterable: true },
    { field: "name", headerName: "Name", width: 470, filterable: true },
    { field: "country", headerName: "Country", width: 330, filterable: true },
    {
      field: "locationCode",
      headerName: "Location Code",
      width: 230,
      filterable: true,
    },
    {
      field: "Actions",
      headerName: "Actions",
      description: "Column Actions.",
      sortable: false,
      flex: 1,
      // render edit end delete icons
      renderCell: (params) => {
        return (
          <div
            style={{
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <IconButton onClick={() => handleEditClick(params.row)}>
              <EditIcon />
            </IconButton>
            <IconButton onClick={() => handleDeleteClick(params.row)}>
              <DeleteIcon />
            </IconButton>
          </div>
        );
      },
    },
  ];
  const handleEditClick = (row: ILocationData) => {
    setUpdateData(row);
    setOpenUpdateModal(true);
  };

  const handleDeleteClick = (row: ILocationData) => {
    setSelectedData(row);
    setOpenDialog(true);
  };

  const handleDeleteData = () => {
    if (selectedData != null) {
      LocationService.remove(selectedData.id).then(() => {
        toast("deleted successfuly");
        setOpenDialog(false);
        refreshTable();
      });
    }
  };

  const handleCancelDeleteData = () => {
    setSelectedData(emptyLocation);
    setOpenDialog(false);
  };
  const handleSearchChange = (field: keyof typeof filters, value: string) => {
    setFilters((prev) => ({ ...prev, [field]: value }));
  };
  // set page to 0 and fetch data
  const handleSearch = () => {
    setPage(0);
    fetchData();
  };

  const fetchData = async () => {
    try {
      LocationService.search(filters, page, pageSize)
        .then((response) => {
          setData(response.data.content);
          setTotalRows(response.data.totalPages * response.data.size);
        })
        .catch((e: IErrorResponse) => ToastComponents.showErrorToast(e));
    } catch (error) {
      console.error("Error while fetching the data", error);
    }
  };
  const handleSave = () => {
    refreshTable();
  };

  const handleUpdateCancel = () => {
    setOpenUpdateModal(false);
    setUpdateData(emptyLocation);
  };
  const handleUpdateConfirm = () => {
    setOpenUpdateModal(false);
    setUpdateData(emptyLocation);
    refreshTable();
  };
  const refreshTable = () => {
    setFilters(emptyLocation);
    setPage(0);
    fetchData();
  };
  const handleClear = () => {
    setFilters(emptyLocation);
  };
  const paginationModel = { page: 0, pageSize: 5 };
  return (
    <div>
      <Paper sx={{ mt: 5, mb: 5, height: 800, width: "100%" }}>
        <div
          style={{
            display: "flex",
            justifyContent: "right",
            alignItems: "right",
          }}
        >
          <LocationModal handleSave={handleSave} />
        </div>

        <Box sx={{ display: "flex", gap: 5, paddingBottom: 2 }}>
          <TextField
            label="Id"
            variant="outlined"
            value={filters.id == null ? "" : filters.id}
            onChange={(e) => handleSearchChange("id", e.target.value)}
          />
          <TextField
            label="Name"
            variant="outlined"
            value={filters.name}
            onChange={(e) => handleSearchChange("name", e.target.value)}
          />
          <TextField
            label="Country"
            variant="outlined"
            value={filters.country}
            onChange={(e) => handleSearchChange("country", e.target.value)}
          />
          <TextField
            label="City"
            variant="outlined"
            value={filters.city}
            onChange={(e) => handleSearchChange("city", e.target.value)}
          />
          <TextField
            label="Location Code"
            variant="outlined"
            value={filters.locationCode}
            onChange={(e) => handleSearchChange("locationCode", e.target.value)}
          />
        </Box>
        <Box
          sx={{
            display: "flex",
            justifyContent: "flex-end",
            gap: 2,
            mt: 2,
            mr: 5,
          }}
        >
          <ButtonComponent
            icon={<SearchIcon />}
            text={"Search"}
            onClick={handleSearch}
          />
          <ButtonComponent
            icon={<ClearIcon />}
            text={"clear"}
            onClick={handleClear}
          />
        </Box>
        <DataGrid
          rows={data}
          columns={columns}
          initialState={{ pagination: { paginationModel } }}
          sx={{ border: 0 }}
          pageSizeOptions={[2, 10, 20]}
          rowCount={totalRows}
          paginationMode="server"
          onPaginationModelChange={(model) => {
            setPage(model.page);
            setPageSize(model.pageSize);
          }}
        />
      </Paper>
      <ConfirmDialog
        message="Are you sure to delete this data? (releted transportation will be deleted)"
        isOpen={openDialog}
        handleCancel={handleCancelDeleteData}
        handleConfirm={handleDeleteData}
      />
      <LocationUpdateModal
        locationData={updateData}
        modelOpen={openUpdateModal}
        handleCancel={handleUpdateCancel}
        handleConfirm={handleUpdateConfirm}
      />
    </div>
  );
}

export default LocationPage;
