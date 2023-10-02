import { Sidebar, Menu, MenuItem, MenuItemStyles, menuClasses } from "react-pro-sidebar";
import { TbDeviceDesktopAnalytics } from "react-icons/tb";
import { BsFilePerson } from "react-icons/bs";

import { INVESTING_TAB } from "../../../constant/INVESTING_TAB";

interface Props {
  activeTab: number;
  setActiveTab: React.Dispatch<React.SetStateAction<number>>;
}

const RankingSidebar = ({ activeTab, setActiveTab }: Props) => {
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
            active={activeTab === INVESTING_TAB.INFO}
            onClick={() => setActiveTab(INVESTING_TAB.INFO)}
          >
            개별 랭킹
          </MenuItem>
          <MenuItem
            icon={<TbDeviceDesktopAnalytics />}
            active={activeTab === INVESTING_TAB.MY_INFO}
            onClick={() => setActiveTab(INVESTING_TAB.MY_INFO)}
          >
            그룹 랭킹
          </MenuItem>
        </Menu>
      </Sidebar>
    </div>
  );
};
export default RankingSidebar;
