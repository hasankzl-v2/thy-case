// loader.ts
let showLoader: () => void;
let hideLoader: () => void;

export const setLoaderFunctions = (show: () => void, hide: () => void) => {
  showLoader = show;
  hideLoader = hide;
};

export const triggerShowLoader = () => showLoader && showLoader();
export const triggerHideLoader = () => hideLoader && hideLoader();