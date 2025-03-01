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
function Layout() {
  return (
    <>
      <ResponsiveAppBar />
      <ToastContainer />
      <Outlet />
    </>
  );
}
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
