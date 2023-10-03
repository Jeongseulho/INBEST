import { Sidebar, Menu, MenuItem, MenuItemStyles, menuClasses } from "react-pro-sidebar";
import { TbDeviceDesktopAnalytics } from "react-icons/tb";
import { BsFilePerson } from "react-icons/bs";

import { RANKING_TAB } from "../../../constant/RANKING_TAB";
import { useNavigate } from "react-router-dom";

interface Props {
  activeTab: number;
  setActiveTab: React.Dispatch<React.SetStateAction<number>>;
}

const RankingSidebar = ({ activeTab, setActiveTab }: Props) => {
  const navigate = useNavigate();
  const theme = {
    sidebar: {
      backgroundColor: "#ffffff",
      color: "#948d8d",
    },
    menu: {
      menuContent: "#ffffff",
      icon: "#948d8d",
      hover: {
        backgroundColor: "#ccffcc",
        color: "#21b621",
      },
      disabled: {
        color: "#99e699",
      },
    },
  };

  const menuItemStyles: MenuItemStyles = {
    root: {
      fontSize: "1rem",
      [`.${menuClasses.active}`]: {
        backgroundColor: theme.menu.hover.backgroundColor,
        color: theme.menu.hover.color,
      },
    },
    icon: {
      color: theme.menu.icon,
    },
    SubMenuExpandIcon: {
      color: "#b6b7b9",
    },
    subMenuContent: () => ({
      backgroundColor: theme.menu.menuContent,
    }),
    button: {
      "&:hover": {
        backgroundColor: theme.menu.hover.backgroundColor,
      },
    },
    label: ({ open }) => ({
      fontWeight: open ? "bold" : undefined,
    }),
  };

  return (
    <div className=" bg-white">
      <Sidebar
        backgroundColor={theme.sidebar.backgroundColor}
        rootStyles={{
          color: theme.sidebar.color,
          minWidth: "220px",
          width: "10%",
          minHeight: "92vh",
          backgroundColor: "#ffffff",
        }}
      >
        <Menu menuItemStyles={menuItemStyles}>
          <MenuItem
            icon={<BsFilePerson />}
            active={activeTab === RANKING_TAB.PERSONAL_RANKING}
            onClick={() => {
              setActiveTab(RANKING_TAB.PERSONAL_RANKING);
              navigate("/ranking");
            }}
          >
            개별 랭킹
          </MenuItem>
          <MenuItem
            icon={<TbDeviceDesktopAnalytics />}
            active={activeTab === RANKING_TAB.GROUP_RANKING}
            onClick={() => {
              setActiveTab(RANKING_TAB.GROUP_RANKING);
              navigate("/ranking/group");
            }}
          >
            그룹 랭킹
          </MenuItem>
        </Menu>
      </Sidebar>
    </div>
  );
};
export default RankingSidebar;
