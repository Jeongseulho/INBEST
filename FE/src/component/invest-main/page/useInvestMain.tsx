import { useState } from "react";

export const useInvestMain = () => {
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showFilterModal, setShowFilterModal] = useState(false);

  const openCreateModal = () => {
    setShowCreateModal(true);
  };

  const closeCreateModal = () => {
    setShowCreateModal(false);
  };

  const openFilterModal = () => {
    setShowFilterModal(true);
  };

  const closeFilterModal = () => {
    setShowFilterModal(false);
  };

  return {
    showCreateModal,
    openCreateModal,
    closeCreateModal,
    showFilterModal,
    openFilterModal,
    closeFilterModal,
  };
};
