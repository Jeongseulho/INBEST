/**
 * @type {import('tailwindcss').Config}
 */
module.exports = {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      fontFamily: {
        light: ["light"],
        regular: ["regular"],
        medium: ["medium"],
        semiBold: ["semibold"],
        bold: ["bold"],
        extraBold: ["extraBold"],
      },
      colors: {
        main: "#6CE061",
        light: "#6FFF8C",
        lima: "#ADF777",
        lime: "#C1E061",
      },
    },
  },
  plugins: [],
};
