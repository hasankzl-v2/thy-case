import { useState, useEffect } from "react";
import ILocationData from "../types/ILocationData";
import LocationService from "../service/LocationService";
import Button from "@mui/material/Button";
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

function LocationPage() {
  const [data, setData] = useState<ILocationData[]>([]);
  const [openDialog, setOpenDialog] = useState(false); // Dialog açık mı kontrolü
  const [selectedData, setSelectedData] = useState<ILocationData | null>(); // Silinecek verinin id'si
  const [loading, setLoading] = useState<boolean>(true);
  const [page, setPage] = useState<number>(0);
  const [pageSize, setPageSize] = useState<number>(10);
  const [totalRows, setTotalRows] = useState<number>(0);
  const [filters, setFilters] = useState<ILocationData>(emptyLocation);
  const [updateData, setUpdateData] = useState<ILocationData>(emptyLocation);
  const [openUpdateModal, setOpenUpdateModal] = useState(false);
  useEffect(() => {
    fetchData();
  }, [page, pageSize]);

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
  const handleSearch = () => {
    setPage(0);
    fetchData();
  };

  const fetchData = async () => {
    setLoading(true);
    try {
      LocationService.search(filters, page, pageSize)
        .then((response) => {
          setData(response.data.content);
          setTotalRows(response.data.totalPages * response.data.size);
        })
        .catch((e: IErrorResponse) => ToastComponents.showErrorToast(e))
        .finally(() => setLoading(false));
    } catch (error) {
      console.error("Error while fetching the data", error);
    } finally {
      setLoading(false);
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
  const paginationModel = { page: 0, pageSize: 5 };
  return (
    <div>
      <Paper sx={{ mt: 5, height: 800, width: "100%" }}>
        <div
          style={{
            display: "flex",
            justifyContent: "right",
            alignItems: "right",
          }}
        >
          <LocationModal handleSave={handleSave} />
        </div>

        <Box sx={{ display: "flex", gap: 2, paddingBottom: 2 }}>
          <TextField
            label="Id"
            variant="outlined"
            size="small"
            value={filters.id}
            onChange={(e) => handleSearchChange("id", e.target.value)}
          />
          <TextField
            label="Name"
            variant="outlined"
            size="small"
            value={filters.name}
            onChange={(e) => handleSearchChange("name", e.target.value)}
          />
          <TextField
            label="Country"
            variant="outlined"
            size="small"
            value={filters.country}
            onChange={(e) => handleSearchChange("country", e.target.value)}
          />
          <TextField
            label="City"
            variant="outlined"
            size="small"
            value={filters.city}
            onChange={(e) => handleSearchChange("city", e.target.value)}
          />
          <TextField
            label="Location Code"
            variant="outlined"
            size="small"
            value={filters.locationCode}
            onChange={(e) => handleSearchChange("locationCode", e.target.value)}
          />
          <Button variant="contained" onClick={handleSearch}>
            Search
          </Button>
        </Box>
        <DataGrid
          rows={data}
          columns={columns}
          initialState={{ pagination: { paginationModel } }}
          sx={{ border: 0 }}
          pageSizeOptions={[2, 10, 20]}
          rowCount={totalRows}
          paginationMode="server"
          loading={loading}
          onPaginationModelChange={(model) => {
            setPage(model.page);
            setPageSize(model.pageSize);
          }}
        />
      </Paper>
      <ConfirmDialog
        message="Are you sure to delete this data?"
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
