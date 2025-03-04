import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import "@fontsource/roboto/300.css";
import "@fontsource/roboto/400.css";
import "@fontsource/roboto/500.css";
import "@fontsource/roboto/700.css";
import { createBrowserRouter, RouterProvider, Outlet } from "react-router-dom";
import App from "./App.tsx";
import LocationPage from "./pages/LocationPage.tsx";
import NotFoundPage from "./pages/NotFoundPage.tsx";
import RoutePage from "./pages/RoutePage.tsx";
import TransportationPage from "./pages/TransportationPage.tsx";
import ResponsiveAppBar from "./components/ResponsiveAppBar.tsx";
import { ToastContainer } from "react-toastify";
import Sidebar from "./components/SideBarComponent.tsx";
import Grid from "@mui/material/Grid2";
import { Box } from "@mui/material";
import Loader from "./components/LoaderComponent.tsx";
import { LoaderProvider } from "./context/LoaderContext.tsx";

/*
layout for every page
LoaderProvider added for update loader context
ToastContainer added for toast
*/
function Layout() {
  return (
    <LoaderProvider>
      <Loader />
      <ToastContainer />
      <Box sx={{ flexGrow: 1 }}>
        <ResponsiveAppBar />
        <Grid container spacing={2}>
          <Grid size={2}>
            <Sidebar />
          </Grid>
          <Grid size={10}>
            <Outlet />
          </Grid>
        </Grid>
      </Box>
    </LoaderProvider>
  );
}

// browser router defination
const router = createBrowserRouter([
  {
    element: <Layout />,
    errorElement: <NotFoundPage />,
    children: [
      {
        path: "/",
        element: <App />,
      },
      {
        path: "/location",
        element: <LocationPage />,
      },
      {
        path: "/route",
        element: <RoutePage />,
      },
      {
        path: "/transportation",
        element: <TransportationPage />,
      },
    ],
  },
]);

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>
);
